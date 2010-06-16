package de.fuberlin.wiwiss.silk.datasource

import de.fuberlin.wiwiss.silk.Instance
import de.fuberlin.wiwiss.silk.util.FileUtils._
import java.io._
import java.util.logging.Logger

/**
 * A partition cache, which caches the partitions on the local file system.
 */
class FilePartitionCache(dir : File) extends PartitionCache
{
    private val logger = Logger.getLogger(getClass.getName)

    private val maxPartitionSize = 1000

    @volatile
    private var _partitionCount =
    {
        if(dir.exists)
        {
            val partitionFiles = dir.list.map(name => name.dropWhile(!_.isDigit)).filter(!_.isEmpty)
            if(partitionFiles.isEmpty)
            {
                0
            }
            else
            {
                partitionFiles.map(_.toInt).max + 1
            }
        }
        else
        {
            0
        }
    }

    @volatile
    private var _isWriting = false

    override def size = _partitionCount

    override def isWriting = _isWriting

    override def apply(index : Int) : Array[Instance] =
    {
        val stream = new ObjectInputStream(new FileInputStream(dir + "/partition" + index))

        try
        {
            val partitionSize = stream.readInt()
            val partition = new Array[Instance](partitionSize)

            for(i <- 0 until partitionSize)
            {
                partition(i) = stream.readObject().asInstanceOf[Instance]
            }

            partition
        }
        finally
        {
            stream.close()
        }
    }

    override def write(instances : Traversable[Instance])
    {
        _isWriting = true

        dir.deleteRecursive()
        dir.mkdirs()

        var curPartition = List[Instance]()
        var curPartitionSize = 0
        _partitionCount = 0
        var instanceCount = 0

        for(instance <- instances)
        {
            curPartition ::= instance
            curPartitionSize += 1
            instanceCount += 1

            if(curPartitionSize == maxPartitionSize)
            {
                writePartition(curPartition)
                _partitionCount += 1

                curPartition = List[Instance]()
                curPartitionSize = 0
            }
        }

        if(curPartitionSize > 0)
        {
            writePartition(curPartition)
            _partitionCount += 1
        }

        logger.info("Completed writing " + instanceCount + " instances")

        _isWriting = false
    }

    private def writePartition(instances : List[Instance])
    {
        val stream = new ObjectOutputStream(new FileOutputStream(dir + "/partition" + _partitionCount))

        try
        {
            stream.writeInt(instances.size)
            for(instance <- instances)
            {
                stream.writeObject(instance)
            }
        }
        finally
        {
            stream.close()
        }

        logger.info("Written partition " + _partitionCount)
    }
}
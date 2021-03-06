resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

// Plugin for generating WAR files.
addSbtPlugin("com.github.play2war" % "play2-war-plugin" % "1.3-beta2")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")

//resolvers += "sbt-newproject-repo" at "http://timt.github.com/repo/releases/"

//addSbtPlugin("org.tbag" % "sbt-newproject" % "0.4")

resolvers ++= Seq("bintray-sbt-plugin-releases" at "http://dl.bintray.com/content/sbt/sbt-plugin-releases")

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.1.1")

resolvers ++= Seq("Sonatype Repo" at "http://oss.sonatype.org/content/groups/public/")


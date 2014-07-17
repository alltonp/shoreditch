//resolvers += "sbt-newproject-repo" at "http://timt.github.com/repo/releases/"

//addSbtPlugin("org.tbag" % "sbt-newproject" % "0.4")

resolvers ++= Seq("bintray-sbt-plugin-releases" at "http://dl.bintray.com/content/sbt/sbt-plugin-releases")

//addSbtPlugin("me.lessis" % "bintray-sbt" % "0.1.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

resolvers ++= Seq("Sonatype Repo" at "http://oss.sonatype.org/content/groups/public/")

resolvers += Classpaths.typesafeResolver

resolvers += Resolver.url("scalasbt", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases")) (Resolver.ivyStylePatterns)

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")

import sbt._

val akkaVersion     = "2.5.25"
val akkaHttpVersion = "10.1.10"
val jacksonVersion  = "2.10.0"
val monocleVersion  = "2.0.0"
val circeVersion    = "0.12.3"
val swaggerVersion  = "2.0.9"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    inThisBuild(
      List(
        scalaVersion := "2.12.8",
        version := "0.1"
      )
    ),
    name := "bbc_sound",
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += "Typesafe Repo" at "https://repo.typesafe.com/typesafe/releases/",
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "javax.ws.rs"                  % "javax.ws.rs-api"       % "2.0.1",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
      "com.typesafe.akka"            %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka"            %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka"            %% "akka-actor"           % akkaVersion,
      "com.typesafe.akka"            %% "akka-stream"          % akkaVersion,
      "com.typesafe.akka"            %% "akka-slf4j"           % akkaVersion,
      "ch.megard"                    %% "akka-http-cors"       % "1.1.1",
      "org.slf4j"                    % "slf4j-simple"          % "1.7.28",
      "org.joda"                     % "joda-convert"          % "1.7",
      "org.scalatest"                %% "scalatest"            % "3.2.9" % Test,
      "com.github.julien-truffaut"   %% "monocle-core"         % monocleVersion,
      "com.github.julien-truffaut"   %% "monocle-macro"        % monocleVersion,
      "com.github.julien-truffaut"   %% "monocle-law"          % monocleVersion % "test",
      "com.typesafe.play"            %% "play-json-joda"       % "2.9.2"
    )
  )

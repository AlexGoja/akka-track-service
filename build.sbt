import sbt._
import com.typesafe.sbt.packager.docker._

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
        version := "1.10.0"
      )
    ),
    name := "bbc-sound-service",
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += "Typesafe Repo" at "https://repo.typesafe.com/typesafe/releases/",
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "javax.ws.rs"                  % "javax.ws.rs-api"           % "2.0.1",
      "com.github.swagger-akka-http" %% "swagger-akka-http"        % "2.0.4",
      "com.github.swagger-akka-http" %% "swagger-scala-module"     % "2.0.5",
      "com.fasterxml.jackson.module" %% "jackson-module-scala"     % jacksonVersion,
      "com.typesafe.akka"            %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka"            %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka"            %% "akka-actor"               % akkaVersion,
      "com.typesafe.akka"            %% "akka-stream"              % akkaVersion,
      "com.typesafe.akka"            %% "akka-slf4j"               % akkaVersion,
      "ch.megard"                    %% "akka-http-cors"           % "0.4.1",
      "org.slf4j"                    % "slf4j-simple"              % "1.7.28",
      "com.github.tototoshi"         %% "slick-joda-mapper"        % "2.3.0",
      "org.joda"                     % "joda-convert"              % "1.7",
      "com.chuusai"                  %% "shapeless"                % "2.3.3",
      "com.lihaoyi"                  %% "pprint"                   % "0.5.3",
      "com.typesafe.play"            %% "play-json"                % "2.6.9",
      "org.julienrf"                 %% "play-json-derived-codecs" % "4.0.0",
      "com.typesafe.play"            %% "play-json-joda"           % "2.6.9",
      "mysql"                        % "mysql-connector-java"      % "5.1.46",
      "com.typesafe.slick"           %% "slick"                    % "3.2.3",
      "com.typesafe.slick"           %% "slick-hikaricp"           % "3.2.3",
      "de.heikoseeberger"            %% "akka-http-circe"          % "1.29.1",
      "org.scalatest"                %% "scalatest"                % "3.0.1" % Test,
    )
  )

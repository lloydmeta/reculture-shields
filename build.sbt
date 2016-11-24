name := """reculture-shields"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.netaporter" %% "scala-uri" % "0.4.16",
  ws
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

reformatOnCompileSettings

// Heroku plugin settings
herokuJdkVersion in Compile := "1.8"
herokuIncludePaths in Compile := Seq(
  "conf"
)
herokuProcessTypes in Compile := Map(
  "web" -> s"target/universal/stage/bin/${name.value} -Dhttp.port=$$PORT"
)
herokuAppName in Compile := resolveHerokuApp()

def resolveHerokuApp() = {
  def fromAppEnv(): Option[String] = {
    val envToName = Map(
      "test" -> "culture-badge-test"
    )
    for {
      env <- sys.props.get("appEnv")
      nameForEnv <- envToName.get(env)
    } yield nameForEnv
  }

  def fromAppName(): Option[String] = sys.props.get("herokuApp")
  fromAppEnv().orElse(fromAppName()).getOrElse("culture-badge-test")
}

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8", // yes, this is 2 args
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code", // N.B. doesn't work well with the ??? hole
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import" // 2.11 only
)

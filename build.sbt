name := """shields-badge"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
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

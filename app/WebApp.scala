import org.culture.OrgId
import play.api.ApplicationLoader.Context
import play.api._
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router
import play.api.routing.sird._
import wiring.Modules

class WebApp extends ApplicationLoader {

  private val module = new Modules
  import module._

  def load(context: Context) =
    new BuiltInComponentsFromContext(context) {

      val router = Router.from {

        case GET(p"/badge/${long(orgId)}.svg") =>
          Action.async {
            badgeBuilder.get(OrgId(orgId)).map { badge =>
              Ok.chunked(badge.body).as("image/svg+xml;charset=utf-8")
            }
          }

      }
    }.application

}

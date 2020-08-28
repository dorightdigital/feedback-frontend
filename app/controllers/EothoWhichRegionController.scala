/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import config.FrontendAppConfig
import connectors.DataCacheConnector
import controllers.actions._
import forms.EothoWhichRegionFormProvider
import javax.inject.Inject
import models.Mode
import navigation.EothoNavigator
import pages.EothoWhichRegionPage
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import views.html.eothoWhichRegion

import scala.concurrent.{ExecutionContext, Future}

class EothoWhichRegionController @Inject()(
  appConfig: FrontendAppConfig,
  override val messagesApi: MessagesApi,
  navigator: EothoNavigator,
  identify: IdentifierAction,
  dataCacheConnector: DataCacheConnector,
  getData: DataRetrievalAction,
  requireData: DataRequiredAction,
  formProvider: EothoWhichRegionFormProvider,
  val controllerComponents: MessagesControllerComponents)(implicit ec: ExecutionContext)
    extends FrontendBaseController with I18nSupport {

  val form = formProvider()

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    val preparedForm = request.userAnswers.get(EothoWhichRegionPage) match {
      case None        => form
      case Some(value) => form.fill(value)
    }

    Ok(eothoWhichRegion(appConfig, preparedForm, mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request =>
      form
        .bindFromRequest()
        .fold(
          formWithErrors => {
            Future.successful(BadRequest(eothoWhichRegion(appConfig, formWithErrors, mode)))
          },
          value => {
            val updatedAnswers =
              request.userAnswers.set(EothoWhichRegionPage, value)

            dataCacheConnector
              .save(updatedAnswers.cacheMap)
              .map(
                _ => Redirect(navigator.nextPage(EothoWhichRegionPage, mode, updatedAnswers))
              )
          }
        )
  }
}
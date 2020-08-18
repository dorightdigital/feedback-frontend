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

package config

import com.google.inject.{Inject, Singleton}
import controllers.routes
import play.api.Configuration
import play.api.i18n.Lang
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class FrontendAppConfig @Inject()(val runModeConfiguration: Configuration, servicesConfig: ServicesConfig) {

  private def getOptionalString(key: String): Option[String] =
    runModeConfiguration.getOptional[String](key)

  private def loadConfig(key: String) =
    getOptionalString(key).getOrElse(throw new Exception(s"Missing configuration key: $key"))

  private lazy val contactHost = getOptionalString("contact-frontend.host").getOrElse("")
  private val contactFormServiceIdentifier = "feedbackfrontend"

  lazy val analyticsToken = loadConfig(s"google-analytics.token")
  lazy val analyticsHost = loadConfig(s"google-analytics.host")
  lazy val gtmContainerId = loadConfig(s"google-tag-manager.containerId")
  lazy val reportAProblemPartialUrl = s"$contactHost/contact/problem_reports_ajax?service=$contactFormServiceIdentifier"
  lazy val reportAProblemNonJSUrl = s"$contactHost/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"
  lazy val betaFeedbackUrl = s"$contactHost/contact/beta-feedback"
  lazy val betaFeedbackUnauthenticatedUrl = s"$contactHost/contact/beta-feedback-unauthenticated"

  lazy val authUrl = servicesConfig.baseUrl("auth")
  lazy val loginUrl = loadConfig("urls.login")
  lazy val loginContinueUrl = loadConfig("urls.loginContinue")

  lazy val privacyPolicyUrl =
    "https://www.gov.uk/government/publications/data-protection-act-dpa-information-hm-revenue-and-customs-hold-about-you/data-protection-act-dpa-information-hm-revenue-and-customs-hold-about-you"
  lazy val urLinkUrl = getOptionalString("microservice.services.features.ur-link-url")

  lazy val pensionSignInUrl = getOptionalString("urls.pension.sign-in")
  lazy val pensionRetirementUrl = getOptionalString("urls.pension.retirement")
  lazy val pensionSideBarOneUrl = getOptionalString("urls.pension.sidebar.link-one")
  lazy val pensionSideBarOneUrlGA = getOptionalString("urls.pension.sidebar.link-one-ga")
  lazy val pensionSideBarTwoUrl = getOptionalString("urls.pension.sidebar.link-two")
  lazy val pensionSideBarTwoUrlGA = getOptionalString("urls.pension.sidebar.link-two-ga")
  lazy val pensionSideBarThreeUrl = getOptionalString("urls.pension.sidebar.link-three")
  lazy val pensionSideBarThreeUrlGA = getOptionalString("urls.pension.sidebar.link-three-ga")
  lazy val pensionSideBarFourUrl = getOptionalString("urls.pension.sidebar.link-four")
  lazy val pensionSideBarFourUrlGA = getOptionalString("urls.pension.sidebar.link-four-ga")
  lazy val pensionSideBarFiveUrl = getOptionalString("urls.pension.sidebar.link-five")
  lazy val pensionSideBarFiveUrlGA = getOptionalString("urls.pension.sidebar.link-five-ga")

  lazy val govUkUrl = loadConfig(s"urls.govUk")

  lazy val languageTranslationEnabled =
    runModeConfiguration.getOptional[Boolean]("microservice.services.features.welsh-translation").getOrElse(true)
  def languageMap: Map[String, Lang] = Map("english" -> Lang("en"), "cymraeg" -> Lang("cy"))
  def routeToSwitchLanguage = (lang: String) => routes.LanguageSwitchController.switchToLanguage(lang)

  lazy val isGtmEnabled = runModeConfiguration.getOptional[Boolean]("google-tag-manager.enabled").getOrElse(true)

}

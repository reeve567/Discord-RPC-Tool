package dev.reeve.discordintegration

import javafx.beans.property.StringProperty
import tornadofx.*

class DiscordIntegration {
	var applicationKey by property<String>()
	fun applicationProperty() = getProperty(DiscordIntegration::applicationKey)

	var details by property<String>()
	fun detailsProperty() = getProperty(DiscordIntegration::details)

	var largeImageKey by property<String>()
	fun largeImageKeyProperty() = getProperty(DiscordIntegration::largeImageKey)

	var largeImageText by property<String>()
	fun largeImageTextProperty() = getProperty(DiscordIntegration::largeImageText)

	var state by property<String>()
	fun stateProperty() = getProperty(DiscordIntegration::state)
}

class DiscordIntegrationModel : ItemViewModel<DiscordIntegration>(DiscordIntegration()) {
	val applicationKey: StringProperty = bind { item?.applicationProperty() }
	val details: StringProperty = bind { item?.detailsProperty() }
	val largeImageKey: StringProperty = bind { item?.largeImageKeyProperty() }
	val largeImageText: StringProperty = bind { item?.largeImageTextProperty() }
	val state: StringProperty = bind { item?.stateProperty() }
}
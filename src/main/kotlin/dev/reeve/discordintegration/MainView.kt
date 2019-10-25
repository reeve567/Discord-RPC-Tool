package dev.reeve.discordintegration

import club.minnced.discord.rpc.DiscordEventHandlers
import club.minnced.discord.rpc.DiscordRPC
import club.minnced.discord.rpc.DiscordRichPresence
import tornadofx.*
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicBoolean

class MainView : View("My View") {
	val model: DiscordIntegrationModel by inject()
	var thread: Thread? = null
	var running = AtomicBoolean(true)

	override fun onUndock() {
		super.onUndock()
		running = AtomicBoolean(false)
	}

	override val root = form {
		fieldset("Discord Integration") {
			field("Application Key") {
				textfield(model.applicationKey) {
					if (config.containsKey(APPLICATION_KEY)) {
						text = config.get(APPLICATION_KEY) as String
					}
					required()
				}
			}
			field("Details") {
				textfield(model.details) {
					with(config) {
						if (containsKey(DETAILS)) {
							text = get(DETAILS) as String
						}
					}
				}
			}
			field("State") {
				textfield(model.state) {
					with(config) {
						if (containsKey(STATE)) {
							text = get(STATE) as String
						}
					}
				}
			}
			field("Large Image Key") {
				textfield(model.largeImageKey) {
					with(config) {
						if (containsKey(LARGE_IMAGE_KEY)) {
							text = get(LARGE_IMAGE_KEY) as String
						}
					}
				}
			}
			field("Large Image Text") {
				textfield(model.largeImageText) {
					with(config) {
						if (containsKey(LARGE_IMAGE_TEXT)) {
							text = get(LARGE_IMAGE_TEXT) as String
						}
					}
				}
			}
		}
		button("Save") {
			action {
				model.commit {
					val integration = model.item
				}

				with(config) {
					set(APPLICATION_KEY, model.applicationKey.get())
					set(DETAILS, model.details.get())
					save()
				}

				thread?.let {
					if (thread!!.isAlive) {
						thread!!.interrupt()
					}
				}
				var lib = DiscordRPC.INSTANCE
				var handlers = DiscordEventHandlers()
				lib.Discord_Initialize(model.applicationKey.get(), handlers, false, null)
				val presence = DiscordRichPresence()
				presence.startTimestamp = System.currentTimeMillis() / 1000
				presence.details = model.details.get()
				presence.largeImageKey = model.largeImageKey.get()
				presence.largeImageText = model.largeImageText.get()
				presence.state = model.state.get()
				lib.Discord_UpdatePresence(presence)

				thread = Thread {
					while (!Thread.currentThread().isInterrupted && running.get()) {
						lib.Discord_RunCallbacks()
						try {
							sleep(2000)
						} catch (e: InterruptedException) {

						}
					}
				}
				thread!!.start()

			}
			enableWhen(model.valid)
		}
	}

	companion object {
		val APPLICATION_KEY = "APPLICATION_KEY"
		val DETAILS = "DETAILS"
		val STATE = "STATE"
		val LARGE_IMAGE_KEY = "LARGE_IMAGE_KEY"
		val LARGE_IMAGE_TEXT = "LARGE_IMAGE_TEXT"
	}
}

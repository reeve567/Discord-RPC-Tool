package dev.reeve.discordrpctool

import club.minnced.discord.rpc.DiscordEventHandlers
import club.minnced.discord.rpc.DiscordRPC
import club.minnced.discord.rpc.DiscordRichPresence
import tornadofx.ConfigProperties

class RPC(config: ConfigProperties, var thread: Thread, val model: DiscordIntegrationModel) {
	init {
		with(config) {
			set(MainView.APPLICATION_KEY, model.applicationKey.get())
			set(MainView.DETAILS, model.details.get())
			set(MainView.STATE, model.state.get())
			set(MainView.LARGE_IMAGE_KEY, model.largeImageKey.get())
			set(MainView.LARGE_IMAGE_TEXT, model.largeImageText.get())
			save()
		}
		discord()
	}

	fun discord() {
		with(thread) {
			if (isAlive) interrupt()
		}

		val lib = DiscordRPC.INSTANCE
		val handlers = DiscordEventHandlers()
		val presence = DiscordRichPresence()

		lib.Discord_Initialize(model.applicationKey.get(), handlers, false, null)

		with(presence) {
			startTimestamp = System.currentTimeMillis() / 1000 //epoch seconds
			details = model.details.get()
			largeImageKey = model.largeImageKey.get()
			largeImageText = model.largeImageText.get()
			state = model.state.get()
		}

		lib.Discord_UpdatePresence(presence)

		thread = DiscordThread()
		thread.start()
	}
}

class DiscordThread : Thread() {
	override fun run() {
		while (!Thread.currentThread().isInterrupted && MainView.running.get()) {
			DiscordRPC.INSTANCE.Discord_RunCallbacks()
			try {
				sleep(2000)
			} catch (ignored: InterruptedException) {
			}
		}
	}
}
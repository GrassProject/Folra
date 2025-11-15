package com.github.grassproject.folra.api.event

import com.github.grassproject.folra.api.FolraPlugin

class FolraPluginRegisterEvent(val plugin: FolraPlugin) : FolraEvent()
class FolraPluginUnregisterEvent(val plugin: FolraPlugin) : FolraEvent()
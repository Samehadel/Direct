package com.direct.app.redis;

import io.lettuce.core.ScriptOutputType;

public interface LuaScriptRunner {
	Object executeLuaScript(String scriptName, ScriptOutputType returnType, String [] keys, String ...arg);
}

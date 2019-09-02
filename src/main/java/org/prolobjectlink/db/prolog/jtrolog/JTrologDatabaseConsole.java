/*
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.prolobjectlink.db.prolog.jtrolog;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.prolobjectlink.db.DatabaseConsole;
import org.prolobjectlink.db.DatabaseServer;
import org.prolobjectlink.db.platform.linux.LinuxDatabaseServer;
import org.prolobjectlink.db.platform.macosx.MacosxDatabaseServer;
import org.prolobjectlink.db.platform.win32.Win32DatabaseServer;
import org.prolobjectlink.db.prolog.AbstractDatabaseConsole;
import org.prolobjectlink.web.platform.UndertowServerControl;
import org.prolobjectlink.web.platform.UndertowWebServer;
import org.prolobjectlink.web.platform.WebPlatformUtil;
import org.prolobjectlink.web.platform.WebServerControl;
import org.prolobjectlink.web.platform.linux.undertow.LinuxUndertowWebServer;
import org.prolobjectlink.web.platform.macosx.undertow.MacosxUndertowWebServer;
import org.prolobjectlink.web.platform.win32.undertow.Win32UndertowWebServer;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JTrologDatabaseConsole extends AbstractDatabaseConsole implements DatabaseConsole {

	public JTrologDatabaseConsole() {
		super(new JTrologDatabaseProvider());
	}

	public static void main(String[] args) {
		new JTrologDatabaseConsole().run(args);
	}

	public WebServerControl getWebServerControl(int port) {
		UndertowWebServer server = null;
		DatabaseServer database = null;
		if (WebPlatformUtil.runOnWindows()) {
			database = new Win32DatabaseServer();
			server = new Win32UndertowWebServer(port);
		} else if (WebPlatformUtil.runOnOsX()) {
			database = new MacosxDatabaseServer();
			server = new MacosxUndertowWebServer(port);
		} else if (WebPlatformUtil.runOnLinux()) {
			database = new LinuxDatabaseServer();
			server = new LinuxUndertowWebServer(port);
		} else {
			Logger.getLogger(UndertowServerControl.class.getName()).log(Level.SEVERE, null, "Not supported platfor");
			System.exit(1);
		}
		return new UndertowServerControl(server, database);
	}

}

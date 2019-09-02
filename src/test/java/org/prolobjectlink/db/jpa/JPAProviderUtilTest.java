/*-
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.jpa;

import static org.junit.Assert.assertEquals;

import javax.persistence.spi.LoadState;
import javax.persistence.spi.ProviderUtil;

import org.junit.Test;
import org.prolobjectlink.BaseTest;

public class JPAProviderUtilTest extends BaseTest {

	private static ProviderUtil PROVIDER_UTIL = new JpaProviderUtil();

	@Test
	public final void testIsLoadedWithoutReference() {
		assertEquals(LoadState.UNKNOWN, PROVIDER_UTIL.isLoadedWithoutReference(null, null));
	}

	@Test
	public final void testIsLoadedWithReference() {
		assertEquals(LoadState.UNKNOWN, PROVIDER_UTIL.isLoadedWithReference(null, null));
	}

	@Test
	public final void testIsLoaded() {
		assertEquals(LoadState.UNKNOWN, PROVIDER_UTIL.isLoaded(null));
	}

}

package org.prolobjectlink.db.jpa;

import static org.junit.Assert.assertEquals;

import javax.persistence.spi.LoadState;
import javax.persistence.spi.ProviderUtil;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.db.jpa.JpaProviderUtil;

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

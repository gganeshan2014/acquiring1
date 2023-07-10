package com.cg.sp.constants;

import java.util.HashMap;
import java.util.Map;

public enum Subscriptions {
	PREMIUM("Premium"),
	STANDARD("Standard");

	private static Map<String, Subscriptions> subscriptionsMap = new HashMap<>();

	static {
		for (Subscriptions subscriptions : Subscriptions.values())
			subscriptionsMap.put(subscriptions.getSubscription().toLowerCase(), subscriptions);
	}

	private final String subscription;

	Subscriptions(String subscription) {
		this.subscription = subscription;
	}

	public String getSubscription() {
		return subscription;
	}

	public static Subscriptions getSubscriptionByValue(String subscription) {
		if (subscription != null)
			return subscriptionsMap.get(subscription.toLowerCase());
		return null;
	}
}

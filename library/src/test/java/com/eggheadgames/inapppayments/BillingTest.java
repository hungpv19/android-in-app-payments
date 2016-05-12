package com.eggheadgames.inapppayments;

import android.content.Context;

import com.billing.BillingServiceListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class BillingTest {

    @Mock
    Context context;

    @Test
    public void onProductOwnedEvent_eachRegisteredListenerShouldBeTriggered() throws Exception {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener firstListener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(firstListener);

        BillingServiceListener secondListener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(secondListener);

        IAPManager.getBillingService().productOwned(TestConstants.TEST_SKU, false);

        Mockito.verify(firstListener, Mockito.times(1)).onProductPurchased(TestConstants.TEST_SKU);
        Mockito.verify(secondListener, Mockito.times(1)).onProductPurchased(TestConstants.TEST_SKU);
    }

    @Test
    public void onProductDetailsFetched_eachRegisteredListenerShouldBeTriggered() {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener firstListener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(firstListener);

        BillingServiceListener secondListener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(secondListener);

        Map<String, String> products = new HashMap<>();
        products.put(TestConstants.TEST_SKU, TestConstants.TEST_PRODUCT);
        products.put(TestConstants.TEST_SKU_1, TestConstants.TEST_PRODUCT);

        IAPManager.getBillingService().updatePrices(products);

        Mockito.verify(firstListener, Mockito.times(1)).onPricesUpdated(products);
        Mockito.verify(secondListener, Mockito.times(1)).onPricesUpdated(products);
    }

    @Test
    public void onIapManagerInteraction_onlyRegisteredListenersShouldBeTriggered() {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener firstListener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(firstListener);

        BillingServiceListener secondListener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(secondListener);

        IAPManager.removeListener(firstListener);

        IAPManager.getBillingService().productOwned(TestConstants.TEST_SKU, false);

        Mockito.verify(firstListener, Mockito.never()).onProductPurchased(Mockito.anyString());
        Mockito.verify(secondListener, Mockito.times(1)).onProductPurchased(TestConstants.TEST_SKU);

    }

    @Test
    public void onProductPurchase_purchaseCallbackShouldBeTriggered() {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener listener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(listener);

        IAPManager.getBillingService().productOwned(TestConstants.TEST_SKU, false);

        Mockito.verify(listener, Mockito.times(1)).onProductPurchased(TestConstants.TEST_SKU);
    }

    @Test
    public void onProductRestore_restoreCallbackShouldBeTriggered() {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener listener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(listener);

        IAPManager.getBillingService().productOwned(TestConstants.TEST_SKU, true);

        Mockito.verify(listener, Mockito.times(1)).onProductRestored(TestConstants.TEST_SKU);
    }

    @Test
    public void onSubscriptionPurchase_purchaseCallbackShouldBeTriggered() {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener listener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(listener);

        IAPManager.getBillingService().subscriptionOwned(TestConstants.TEST_SKU, false);

        Mockito.verify(listener, Mockito.times(1)).onSubscriptionPurchased(TestConstants.TEST_SKU);
    }

    @Test
    public void onSubscriptionRestore_restoreCallbackShouldBeTriggered() {
        IAPManager.build(context, IAPManager.BUILD_TARGET_GOOGLE, new ArrayList<String>());

        BillingServiceListener listener = Mockito.spy(BillingServiceListener.class);
        IAPManager.addListener(listener);

        IAPManager.getBillingService().subscriptionOwned(TestConstants.TEST_SKU, true);

        Mockito.verify(listener, Mockito.times(1)).onSubscriptionRestored(TestConstants.TEST_SKU);
    }

}
package vn.tiki.TiniAppExample

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import vn.tiki.tiniappsdk.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rn_activity)
        val tiniButton = findViewById<Button>(R.id.open_tini_app)
        initTiniAppSDK()
        tiniButton.setOnClickListener(View.OnClickListener {
            TiniAppSDK.getInstance().openMiniApp(this, "com.tini.appstore", null, null)
        })
    }

    private fun initTiniAppSDK() {
        val tiniAppConfigBuilder = TiniAppConfiguration.Builder()
        tiniAppConfigBuilder.setClientId("76e643c9-5239-444f-a3e0-c777fd0cec09")
        tiniAppConfigBuilder.setPartnerCode("app-demo")
        tiniAppConfigBuilder.setEnv(TiniAppConfiguration.TiniSDKEnv.UAT)
        tiniAppConfigBuilder.registerTiniAppCallback(object : TiniAppInterface {
            override fun closeApp(activity: Activity) {
                activity.finish()
            }

            override fun openPayment(
                transactionId: String,
                amount: Double,
                callback: TiniAppCallback<Bundle>
            ) {
                val bundle = Bundle()
                bundle.putString("transactionId", transactionId)
                callback?.onSuccess(bundle)
            }

            override fun getUserInfo(callback: TiniAppCallback<Bundle>?) {
                val bundle = Bundle()
                bundle.putString("name", "Test user")
                bundle.putString("email", "Test user")
                callback?.onSuccess(bundle)
            }
        })
        TiniAppSDK.sdkInit(this.applicationContext, tiniAppConfigBuilder.build())
    }
}
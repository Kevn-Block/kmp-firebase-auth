//
// Created by Kevin Block on 5/31/25.
//

import Foundation
import UIKit
import ComposeApp

class AppDelegate: NSObject, UIApplicationDelegate {

    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {

        print("AppDelegate: didFinishLaunchingWithOptions - Calling KMP init.")

        // --- Call your KMP Initializer HERE ---
        KMPInitializerKt.onDidFinishLaunchingWithOptions()

        return true
    }

    // You can add other AppDelegate methods here if needed (e.g., for push notifications)
}

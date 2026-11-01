import SwiftUI
import ComposeApp

// Deep links: woorm:// URLs (registered in Info.plist CFBundleURLTypes) are
// forwarded to the Kotlin Multiplatform side, whose shared NavHost matches
// them against the navDeepLink graph (see ExternalUriHandler / Navigation.kt).
class AppDelegate: NSObject, UIApplicationDelegate {
	func application(
		_ application: UIApplication,
		open uri: URL,
		options: [UIApplication.OpenURLOptionsKey: Any] = [:]
	) -> Bool {
		ExternalUriHandler.shared.onNewUri(uri: uri.absoluteString)
		return true
	}
}

@main
struct iOSApp: App {
	@UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}

Pod::Spec.new do |spec|
    spec.name                     = 'api_request_utils'
    spec.version                  = '1.0.1'
    spec.homepage                 = ''
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Request-Utilities is a Kotlin Multiplatform library that provides a simple and efficient way to make HTTP requests in Kotlin Multiplatform projects. Request-Utilities is designed to be easy to use, customizable, and extendable.'
    spec.vendored_frameworks      = 'build/cocoapods/framework/api-request-utils.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':api-request-utils',
        'PRODUCT_MODULE_NAME' => 'api-request-utils',
    }
                
    spec.script_phases = [
        {
            :name => 'Build api_request_utils',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end
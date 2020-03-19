import groovy.*

if (main() == "SUCCESS"){
    echo("Main Success")
} else {
    echo("Main Failure")
    currentBuild.result = "UNSTABLE"
    return
}


def main() {
    def retVal = "SUCCESS"
	try {
		node {
			echo("Starting the pipeline")
			stage('Checkout the code') {

                
                echo("Options ::")
                echo("Branch for code checkout : $env.BRANCH_NAME")

				echo("Checking out the code")
				gitCheckout("https://github.com/adityy/adityy_sharma.git", env.BRANCH_NAME)
                
                // Check if version already exisits
				}
			stage('Run tests') {
		        	sh """
				echo "Stage test"
				chmod 755 testscript1.sh
				./testscript1.sh
				"""
			}	
			stage('Deployment') {
				echo ("Deploying the content")
				sh """
				echo "Uploading the repository content to the s3 bucket configured for static website hosting"
			    aws s3 cp index.html s3://aditi-test-bucket-website
				echo " The website is accesible at http://aditi-test-bucket-website.s3-website.us-east-2.amazonaws.com"
				"""
		}
	}
		
	} catch (Exception ex) {
		echo("an exception while execution", ex)
		retVal = "FAILURE"
	}
	return retVal
}


// Functions

// Functions
def gitCheckout(def gitURL, def gitBranch){

    checkout changelog: false, poll: false, scm: [
        $class: 'GitSCM',
        branches: [[name: gitBranch]],
        doGenerateSubmoduleConfigurations: false,
        submoduleCfg: [],
        userRemoteConfigs: [
            [
                credentialsId: 'adityy_github',
                url: "${gitURL}"
            ]
        ]
    ]
}




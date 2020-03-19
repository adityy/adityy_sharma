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
                echo("Branch for code checkout : ${params.BRANCH_NAME}")
				
				// Clean up workspace area
				sh """ rm -rf 
				"""
				echo("Checking out the code")
				gitCheckout("https://github.com/adityy/adityy_sharma.git", 'master')
                
                // Check if version already exisits
				}
			stage('Run tests') {
		        	sh """
				echo "Stage test"
				./testscript1.sh
			}	
			stage('Deployment') {
				echo ("Deploying the content")
				sh """
				echo "Uploading the repository content to the s3 bucket configured for static website hosting"
			    aws s3 cp index.html s3://aditi-test-bucket-website
				echo " The website is accesible at http://test-aditi-bucket.s3-website.eu-west-2.amazonaws.com/"
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
                credentialsId: 'github-user',
                url: "${gitURL}"
            ]
        ]
    ]
}




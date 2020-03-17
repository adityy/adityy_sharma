

## About
This application is to host a static-website .



### Prerequisites
WEBSITE DNS : https://my-test-website.com
S3 URL : s3://my-bucket-name



## Steps to execute the job
1. The script is executed through a Jenkins job with branch name as parameter.
2. It triggers a groovy pipeline script which checks-out the code from mentioned repository and runs tests on it.
3. The deployment part uploads the index.html to the S3 bucket which is configured to host the static-website mapped with DNS https://my-test-website.com.

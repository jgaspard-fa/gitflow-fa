
properties([[$class: 'ParametersDefinitionProperty', parameterDefinitions: [[$class: 'hudson.model.StringParameterDefinition', defaultValue: 'top10dependency', description: 'Some Description', name : 'repository'], [$class: 'hudson.model.StringParameterDefinition', defaultValue: 'source-javadoc,unit', description: 'Some Description', name: 'profile']]]])

node {
    
   // Mark the code checkout 'stage'....
   stage 'Checkout'
   
   print "DEBUG: parameter repository = ${repository}"

   // Get some code from a GitHub repository
   //non git url: 'git@github.com:financeactive/' + repository + '.git'
   //ok git url: 'https://github.com/jgaspard-fa/' + repo + '.git'
   //non git url: 'https://github.com/financeactive/' + repository
   git branch: 'develop', url: 'git@github.com:financeactive/' + repository + '.git'

   // Get the maven tool.
   // ** NOTE: This 'M3' maven tool must be configured
   // **       in the global configuration.           
   def mvnHome = tool 'M2'

   // Mark the code build 'stage'....
   stage 'Build'
   // Run the maven build
   sh "${mvnHome}/bin/mvn -Dmaven.test.skip=true -P${profile} clean package"
   step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
   
   step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: emailextrecipients(['jgaspard@financeactive.com'])])
}
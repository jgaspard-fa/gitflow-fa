
properties([[$class: 'ParametersDefinitionProperty', parameterDefinitions: [[$class: 'hudson.model.StringParameterDefinition', defaultValue: 'top10dependency', description: 'Some Description'
, name : 'repository'], [$class: 'hudson.model.StringParameterDefinition', defaultValue: 'source-javadoc,unit', description: 'Some Description', name: 'profile']]]])

node {
    
   // Mark the code checkout 'stage'....
   stage 'Checkout'
   
   print "DEBUG: parameter repository = ${repository}"
   print "DEBUG: parameter profile = ${profile}"
   def profileUnit = profile.indexOf('unit');
   def profileIntegration = profile.indexOf('integration');
   def profileCoverage = profile.indexOf('coverage');
   println "DEBUG: profileUnit " + profileUnit
   println "DEBUG: profileIntegration " + profileIntegration
   println "DEBUG: profileCoverage " + profileCoverage
   
   def profileTest = (profileUnit != -1) || (profileIntegration != -1) || (profileCoverage != -1) 
   println "DEBUG: profileTest " + profileTest
   
   sh('echo $M2_HOME')
   sh('echo $M2_HOME > ECHO')
   def stdout = readFile('ECHO').trim()
   print "out: " + stdout
   
   sh "export M2_HOME=/home/service/maven3"
   sh('echo $M2_HOME > ECHO')
   def stdout2 = readFile('ECHO').trim()
   print "out: " + stdout2

   // Get some code from a GitHub repository
   //non git url: 'git@github.com:financeactive/' + repository + '.git'
   //ok git url: 'https://github.com/jgaspard-fa/' + repo + '.git'
   //non git url: 'https://github.com/financeactive/' + repository
   git branch: 'develop', url: 'git@github.com:financeactive/' + repository + '.git'

   // Get the maven tool.
   // ** NOTE: This 'M3' maven tool must be configured
   // **       in the global configuration.           
   def mvnHome = tool 'maven3'

   // Mark the code build 'stage'....
   stage 'Build'
   
   // Run the maven build   
   // org.jenkinsci.plugins.scriptsecurity.sandbox.RejectedAccessException: Scripts not permitted to use method groovy.lang.GString plus java.lang.String
   //def buildCommand = "${mvnHome}" + '/bin/mvn -V  -P' + "${profile}" + ' clean package'
   
   withEnv(['M2_HOME=/home/service/maven3', 'JAVA_HOME=/home/service/jdk1.8']) {
        sh "${mvnHome}/bin/mvn -V -Dmaven.test.skip=true -P${profile} clean package"
   }
   
   if (profileTest)
        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
   
   step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'jgaspard@financeactive.com'])
}
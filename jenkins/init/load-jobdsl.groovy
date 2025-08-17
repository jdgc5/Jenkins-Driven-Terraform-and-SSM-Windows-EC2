import javaposse.jobdsl.plugin.JenkinsJobManagement
import javaposse.jobdsl.dsl.DslScriptLoader

def jm = new JenkinsJobManagement(System.out, [:], new File('/var/jenkins_home'))
def seedFile = new File('/usr/share/jenkins/ref/jobs-dsl/seed.groovy')
if (seedFile.exists()) {
  new DslScriptLoader(jm).runScript(seedFile.text)
  println "Job DSL seed executed from ${seedFile}"
} else {
  println "Job DSL seed file not found: ${seedFile}"
}

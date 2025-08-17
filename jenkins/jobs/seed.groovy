import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this
String repo = System.getenv('MONOREPO_URL') ?: 'CHANGE_ME_GIT_URL'
String branch = 'main'
String agentLabel = 'ansible-terraform'

def mkPipeline = { name, jenkinsfile ->
  factory.pipelineJob(name) {
    description("Pipeline ${name} from ${jenkinsfile}")
    logRotator { numToKeep(30) }
    definition {
      cpsScm {
        scm {
          git {
            remote { url(repo) }
            branch(branch)
            extensions { shallowClone() }
          }
        }
        scriptPath(jenkinsfile)
      }
    }
    properties {
      disableConcurrentBuilds()
    }
    triggers {}
    // Set default agent label inside Jenkinsfile as needed
  }
}

mkPipeline('0-network', 'jenkins/pipelines/0-network.Jenkinsfile')
mkPipeline('1-iam',     'jenkins/pipelines/1-iam.Jenkinsfile')
mkPipeline('2-compute', 'jenkins/pipelines/2-compute.Jenkinsfile')
mkPipeline('3-dns',     'jenkins/pipelines/3-dns.Jenkinsfile')
mkPipeline('99-ec2-update', 'jenkins/pipelines/99-ec2-update.Jenkinsfile')
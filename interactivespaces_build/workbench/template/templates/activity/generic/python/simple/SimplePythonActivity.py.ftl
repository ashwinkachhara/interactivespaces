from interactivespaces.activity.impl import BaseActivity

class SimplePythonActivity(BaseActivity):
    def onActivitySetup(self):
        self.log.info("Activity ${project.activityDescription.identifyingName} setup")

    def onActivityStartup(self):
        self.log.info("Activity ${project.activityDescription.identifyingName} startup")

    def onActivityActivate(self):
        self.log.info("Activity ${project.activityDescription.identifyingName} activated")

    def onActivityDeactivate(self):
        self.log.info("Activity ${project.activityDescription.identifyingName} deactivated")

    def onActivityShutdown(self):
        self.log.info("Activity ${project.activityDescription.identifyingName} shutting down")

    def onActivityCleanup(self):
        self.log.info("Activity ${project.activityDescription.identifyingName} cleanup")
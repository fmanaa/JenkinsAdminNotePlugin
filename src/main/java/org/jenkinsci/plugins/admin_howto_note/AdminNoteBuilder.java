package org.jenkinsci.plugins.admin_howto_note;
import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;


public class AdminNoteBuilder extends Builder {

    private final String adminNoteInJobConfig;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public AdminNoteBuilder(String adminNoteInJobConfig) {
        this.adminNoteInJobConfig = adminNoteInJobConfig;
    }

    /**
     * We'll use this from the <tt>config.jelly</tt>.
     */
    public String getAdminNoteInJobConfig() {
        return adminNoteInJobConfig;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
			//Nothing to do during build time
        return true;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    /**
     * Descriptor for {@link AdminNoteBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        private String htmlNoteInGlobalConfigField;

        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Note from Jenkins Admin";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

            htmlNoteInGlobalConfigField = formData.getString("htmlNoteFromGlobalConfig");
            save();
            return super.configure(req,formData);
        }


        public String gethtmlNoteInGlobalConfigField() {
            return htmlNoteInGlobalConfigField;
        }
    }
}


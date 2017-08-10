package com.ipampas.platform.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.spring.web.plugins.Docket;

@ConfigurationProperties(prefix = SpringFoxProperties.PREFIX)
public class SpringFoxProperties {

    public static final String PREFIX = "springfox";

    private Boolean enabled = Boolean.FALSE;

    private String groupName = Docket.DEFAULT_GROUP_NAME;

    private String host;

    private ApiInfoConfig apiInfo = new ApiInfoConfig(
            "Api Documentation",
            "Api Documentation Description",
            "1.0.0",
            "Terms of Service Url",
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            "Gavin Hu",
            "https://github.com/ipampas/platform",
            "huhz1986@gmail.com"
    );

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ApiInfoConfig getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfoConfig apiInfo) {
        this.apiInfo = apiInfo;
    }

    public class ApiInfoConfig {

        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;

        public ApiInfoConfig() {
        }

        public ApiInfoConfig(String title, String description, String version, String termsOfServiceUrl, String license, String licenseUrl, String contactName, String contactUrl, String contactEmail) {
            this.title = title;
            this.description = description;
            this.version = version;
            this.termsOfServiceUrl = termsOfServiceUrl;
            this.license = license;
            this.licenseUrl = licenseUrl;
            this.contactName = contactName;
            this.contactUrl = contactUrl;
            this.contactEmail = contactEmail;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }
    }
}

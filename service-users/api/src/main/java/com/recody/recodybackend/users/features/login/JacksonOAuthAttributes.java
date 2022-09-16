package com.recody.recodybackend.users.features.login;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

public abstract class JacksonOAuthAttributes {
    private SocialProvider socialProvider;
    @Getter
    private JsonNode attributes;
    
    private JacksonOAuthAttributes(JsonNode attributes){
        this.attributes = attributes;
    }
    
    public static OAuthAttributesBuilder of(SocialProvider socialProvider) {
        return new OAuthAttributesBuilder(socialProvider);
    }
    
    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getProfileImageUrl();
    public abstract SocialProvider getSocialProvider();
    
    @Override
    public String toString() {
        return "{\"JacksonOAuthAttributes\":{" + "\"socialProvider\":" + ((socialProvider != null) ? ("\"" + socialProvider + "\"") : null) + ", \"attributes\":" + attributes + "}}";
    }
    
    /* Builder Class */
    public static class OAuthAttributesBuilder{
        private SocialProvider socialProvider;
        private JsonNode attributes;
        
        public OAuthAttributesBuilder(SocialProvider socialProvider) {
            this.socialProvider = socialProvider;
        }
    
        public OAuthAttributesBuilder attributes(JsonNode attributes) {
            this.attributes = attributes;
            return this;
        }
        
        public JacksonOAuthAttributes build(){
            switch (this.socialProvider){
                case GOOGLE: return new GoogleOAuthAttributes(attributes);
                case NAVER: return new NaverOAuthAttributes(attributes);
                default: return null;
            }
        }
    }
    
    
    
    /* Google Attribute */
    public static class GoogleOAuthAttributes extends JacksonOAuthAttributes {
    
        public GoogleOAuthAttributes(JsonNode attributes) {
            super(attributes);
        }
        
        @Override
        public String getId(){
            return getAttributes().get("id").asText();
        }
        
        @Override
        public String getName(){
            return getAttributes().get("name").asText();
        }
    
        @Override
        public String getEmail() {
            return getAttributes().get("email").asText();
        }
    
        @Override
        public String getProfileImageUrl() {
            return getAttributes().get("picture").asText();
        }
    
        @Override
        public SocialProvider getSocialProvider() {
            return SocialProvider.GOOGLE;
        }
    }
    
    
    
    /* Naver Attribute */
    static class NaverOAuthAttributes extends JacksonOAuthAttributes {
    
        public NaverOAuthAttributes(JsonNode attributes) { super(attributes); }
    
        @Override
        public String getId() {
            JsonNode response = getAttributes().get("response");
            if (response == null) return null;
            
            return response.get("id").asText();
        }
    
        @Override
        public String getName() {
            JsonNode response = getAttributes().get("response");
            if (response == null) return null;
            return response.get("nickName").asText();
        }
    
        @Override
        public String getEmail() {
            JsonNode response = getAttributes().get("response");
            if (response == null) return null;
            return response.get("email").asText();
        }
    
        @Override
        public String getProfileImageUrl() {
            JsonNode response = getAttributes().get("response");

            if (response == null) return null;
            return response.get("profile_image").asText();
        }
    
        @Override
        public SocialProvider getSocialProvider() {
            return SocialProvider.NAVER;
        }
    
    }
}

package com.recody.recodybackend.catalog.features;

import lombok.Value;

@Value(staticConstructor = "of")
public class CategoryIconUrl {
    String iconUrl;
    
    private CategoryIconUrl(String iconUrl) {
        if ( iconUrl == null ) {
            this.iconUrl = null;
        } else {
            // 별다른 검증은 하지 않는다.
            this.iconUrl = preprocess(iconUrl);
        }
    }
    
    private static String preprocess(String iconUrl) {
        return iconUrl.trim();
    }
    
    
    @Override
    public String toString() {
        return "{\"CategoryIconUrl\":{"
               + "\"iconUrl\":" + ((iconUrl != null) ? ("\"" + iconUrl + "\"") : null)
               + "}}";
    }
}

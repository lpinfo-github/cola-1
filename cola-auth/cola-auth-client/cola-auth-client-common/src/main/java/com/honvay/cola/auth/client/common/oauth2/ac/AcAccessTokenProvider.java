package com.honvay.cola.auth.client.common.oauth2.ac;

import com.honvay.cola.auth.client.common.oauth2.openid.OpenIdResourceDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * created on 2019/1/15
 **/
public class AcAccessTokenProvider extends OAuth2AccessTokenSupport implements AccessTokenProvider {

	@Override
	public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException {
		AcResourceDetails resource = (AcResourceDetails) details;
		return retrieveToken(request, resource, getParametersForTokenRequest(resource, request), new HttpHeaders());
	}

	@Override
	public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
		return resource.getClass().equals(AcResourceDetails.class);
	}

	@Override
	public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource, OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("grant_type", "refresh_token");
		form.add("refresh_token", refreshToken.getValue());
		return retrieveToken(request, resource, form, new HttpHeaders());
	}

	@Override
	public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
		return supportsResource(resource);
	}

	private MultiValueMap<String, String> getParametersForTokenRequest(AcResourceDetails resource, AccessTokenRequest request) {

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("grant_type", "ac");
		form.set("authorizationCode", resource.getAuthorizationCode());
		form.set("provider", resource.getProvider());
		form.putAll(request);

		if (resource.isScoped() && resource.getScope() != null) {
			form.set("scope", String.join(" ", resource.getScope()));
		}
		return form;

	}
}


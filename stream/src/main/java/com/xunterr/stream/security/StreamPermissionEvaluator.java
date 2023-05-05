package com.xunterr.stream.security;

import com.xunterr.stream.model.Stream;
import com.xunterr.stream.service.StreamService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Component
public class StreamPermissionEvaluator implements PermissionEvaluator {

	private final StreamService service;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainID, Object permission) {
		if ((authentication == null) || (targetDomainID == null) || !(permission instanceof String)){
			return false;
		}

		return isResourceOwnedBy(authentication, targetDomainID);
	}

	@Override
	public boolean hasPermission(
			Authentication authentication, Serializable targetId, String targetType, Object permission) {
		if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(authentication, targetType, permission.toString().toUpperCase())
				&& isResourceOwnedBy(authentication, targetId);
	}

	private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
		for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
			if (grantedAuth.getAuthority().startsWith(targetType) &&
					grantedAuth.getAuthority().contains(permission)) {
				return true;
			}
		}
		return false;
	}

	private boolean isResourceOwnedBy(Authentication auth, Object targetId){
		UUID userID = (UUID) auth.getPrincipal();
		UUID resourceID = (UUID) targetId;

		Stream resource = service.getById(resourceID);
		return resource.getUserID().equals(userID);
	}
}

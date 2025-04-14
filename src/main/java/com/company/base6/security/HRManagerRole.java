package com.company.base6.security;

import com.company.base6.entity.Department;
import com.company.base6.entity.Step;
import com.company.base6.entity.User;
import com.company.base6.entity.UserStep;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "HR manager", code = HRManagerRole.CODE, scope = "UI")
public interface HRManagerRole {
    String CODE = "hr-manager";

    @MenuPolicy(menuIds = {"User.list", "MyOnboardingView"})
    @ViewPolicy(viewIds = {"User.list", "MyOnboardingView", "User.detail"})
    void screens();

    @EntityPolicy(entityClass = Department.class, actions = EntityPolicyAction.READ)
    void department();

    @EntityPolicy(entityClass = Step.class, actions = EntityPolicyAction.READ)
    void step();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();

    @EntityAttributePolicy(entityClass = UserStep.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = UserStep.class, actions = EntityPolicyAction.ALL)
    void userStep();
}
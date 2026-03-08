# Credential Rotation & Incident Response Plan

## Scope
This plan covers the credentials previously exposed in `src/main/resources/application.properties`.

## Immediate Containment (Completed)
- Removed hardcoded secrets from source control and replaced them with environment variable placeholders.
- Enabled CI-based secret scanning to prevent reintroduction.

## Rotation Steps (Execute Immediately)
1. **Invalidate exposed credentials**
   - Rotate all corresponding accounts/secrets in IAM, database, and service accounts.
   - Ensure newly generated values are stored in an approved secret backend (Vault/KMS/Kubernetes Secret).
2. **Redeploy dependent services**
   - Update deployment manifests/runtime environment so applications consume the new secret values.
   - Restart services to load rotated credentials.
3. **Review and restrict privileges**
   - Verify each rotated credential follows least-privilege.
   - Remove unused credentials and disable dormant accounts.
4. **Audit access logs**
   - Review authentication/activity logs for all systems tied to leaked credentials for suspicious activity.
   - Time window: from first commit containing leaked values until completion of rotation.
5. **Escalation and response**
   - If anomalous usage is detected, open a security incident and perform incident response (containment, forensics, notification).

## Verification Checklist
- [ ] No hardcoded credentials remain in repository history for active branches.
- [ ] Secret scan CI is required and passing.
- [ ] All leaked credentials have been rotated and old values revoked.
- [ ] Log review completed and documented with owner/date.

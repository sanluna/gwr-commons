# gwr-commons
This code is used as dependency in most or all Sanluna backend services handling the most basic CRUD operations as well as setting up basic beans and configurations.<br>
<br>
In latest pushes i decided to merge the different dependencies together namely:
* gwr-security
* gwr-multitenancy
* gwr-member-client
* gwr-tenant-client

Since these packages all depended on each other and is overall used by every single service this was a no brainer to me.
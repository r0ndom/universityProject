<#import "layout/layout.ftl" as layout/>
<@layout.layout>
<div style="margin-top: 10%">
    <#if error??>
        <div class="row">
            <div class="alert alert-danger col-md-6 col-md-offset-3" role="alert">
                Your login attempt was not successful, try again.<br/>
                Caused :${Session["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </div>
    </#if>
    <div class="row  col-md-6 col-md-offset-4">
        <form name="loginForm" action="<@spring.url "/j_spring_security_check"/>" method="POST"
              style="margin-left: 30px">
            <div class="row">
                <div class="col-md-4"><h3><@spring.message "login.signIn" /></h3></div>
                <div class="col-md-3" style="text-align: right; margin-top: 20px">
                    <h5><a href="<@spring.url "/register" />"
                           style="text-decoration: underline;"><@spring.message "login.signUp" /></a></h5></div>
            </div>
            <div class="row">
                <div class="col-md-7" style="margin-bottom: 10px;">
                    <input class="form-control" id="username" name="j_username"
                           placeholder="<@spring.message "login.email" />" type="text" style="height: 38px;">
                </div>
            </div>
            <div class="row">
                <div class="col-md-7" style="margin-bottom: 10px;">
                    <input class="form-control" id="password" name="j_password"
                           placeholder="<@spring.message "login.password" />" type="password"
                           style="height: 38px; padding-right: 44px;">


                    <button id="hideShowPassword" aria-pressed="false"
                            style="position: absolute; right: 15px; top: 0px;"
                            class="hideShowPassword-toggle" tabindex="0"
                            aria-label="Show Password" role="button" type="button" onclick="showPasswordLogin()">Show
                    </button>


                </div>
            </div>

            <div class="row" style="margin-bottom: 5px">
                <div class="col-md-7">
                    <input type="checkbox" name="_spring_security_remember_me">
                    <@spring.message "login.rememberMe" /></div>
            </div>
            <div class="row">
                <div class="col-md-7">
                    <input class="btn btn-success" style="width: 100%; height: 40px"
                           value="<@spring.message "login.signIn" />" type="submit"></div>
            </div>
        </form>
    </div>

    <#--<div class="row  col-md-6 col-md-offset-4">-->
        <#--<!-- FACEBOOK SIGNIN &ndash;&gt;-->
        <#--<form name="facebook_signin" id="facebook_signin" action="<@spring.url "/auth/facebook" />" method="POST">-->
            <#--<input type="hidden" name="scope" value="public_profile, email" />-->
            <#--<button type="submit">Sign In with Facebook</button>-->
        <#--</form>-->

        <#--<!-- VKONTAKTE SIGNIN &ndash;&gt;-->
        <#--<form name="vk_signin" id="vk_signin" action="<@spring.url "/auth/vkontakte" />" method="POST">-->
            <#--<input type="hidden" name="scope" value="notify, photos, email"/>-->
            <#--<button type="submit">Sign In with Vkontakte</button>-->
        <#--</form>-->

        <#--<!-- GOOGLE SIGNIN &ndash;&gt;-->
        <#--<form name="gp_signin" id="gp_signin" action="<@spring.url "/auth/google" />" method="POST">-->
            <#--<input type="hidden" name="scope" value="email https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me" />-->
            <#--<button type="submit">Sign in with Google</button>-->
        <#--</form>-->
    <#--</div>-->
</div>
<script>


    function showPasswordLogin() {
        var showClass = "hideShowPassword-toggle-hide"
        var wink = document.getElementById("hideShowPassword");
        var passwordField = $('#password').get(0);

        if (passwordField.type == 'password') {
            passwordField.type = 'text';
            addClass(wink, showClass);

        } else {
            passwordField.type = 'password';
            removeClass(wink, showClass);

        }
    }

    function addClass(o, c) {
        var re = new RegExp("(^|\\s)" + c + "(\\s|$)", "g")
        if (re.test(o.className)) return
        o.className = (o.className + " " + c).replace(/\s+/g, " ").replace(/(^ | $)/g, "")
    }

    function removeClass(o, c) {
        var re = new RegExp("(^|\\s)" + c + "(\\s|$)", "g")
        o.className = o.className.replace(re, "$1").replace(/\s+/g, " ").replace(/(^ | $)/g, "")
    }
</script>
</@layout.layout>

<template>
  <div class="warehouse-login flex">
    <div class="login-container flex">
      <div class="logo"></div>
      <div class="login-img"></div>
      <div class="login-form flex">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" autocomplete="on" style="width: 100%">
          <div class="huanyin-title">
            <span>您好！</span>
            <span>欢迎登录仓储云SAAS管理平台</span>
            <input v-model="loginForm.username" class="login1 inp_bg1" type="text" name="name5" id="name5" value="" placeholder="请输入用户名" />
            <input v-model="loginForm.password" class="login1 inp_bg2" type="password" name="pwd5" id="pwd5" value="" placeholder="请输入密码" />
            <div class="code-container">
              <input v-model="loginForm.code" class="login1 inp_bg3" name="pwd5" id="pwd5" value="" placeholder="请输入验证码" @keyup.enter="handleLogin" />
              <img :src="codeUrl" @click="getCode" class="login-code" />
            </div>
          </div>
          <input class="login2" type="button" name="btn5" id="btn5" value="登录" @click="handleLogin" />
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
      },
      loginRules: {
        username: [
          {
            required: true,
            trigger: "blur",
            message: "请输入您的账号",
          },
        ],
        password: [
          {
            required: true,
            trigger: "blur",
            message: "请输入您的密码",
          },
        ],
        code: [
          {
            required: true,
            trigger: "change",
            message: "请输入验证码",
          },
        ],
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then((res) => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, {
              expires: 30,
            });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30,
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30,
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router
                .push({
                  path: this.redirect || "/",
                })
                .catch(() => {});
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                this.getCode();
              }
            });
        }
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.warehouse-login {
  background-image: url(../assets/images/bg.png);
  background-size: 100% 100%;
  height: 100%;
  width: 100%;
}
.login-container {
  width: 70%;
  height: 70%;
  border-radius: 24px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 0%, rgba(94, 237, 12, 0.2) 100%), rgba(255, 255, 255, 1);
  box-shadow: rgb(25, 139, 19) 0px 0px 9px 3px;
  position: relative;
}
.login-img {
  background: url(../assets/images/login.png) no-repeat;
  height: 50%;
  width: 50%;
  background-size: contain;
}
.login-form {
  width: 50%;
  flex-direction: column;
}
.huanyin-title {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.huanyin-title span:nth-child(1) {
  font-size: 20px;
}
.huanyin-title span:nth-child(2) {
  color: #121212;
  font-size: 22px;
}
.login1 {
  height: 42px;
  width: 80%;
  border: none;
  border-bottom: 1px solid #d8d8d8;
  margin-top: 20px;
  line-height: 42px;
  padding-left: 42px;
}

.inp_bg1 {
  background: url(../assets/images/login1.png) 8px center no-repeat;
  outline: none;
}
.inp_bg2 {
  background: url(../assets/images/login2.png) 8px center no-repeat;
  outline: none;
}
.inp_bg3 {
  background: url(../assets/images/code.png) 8px center no-repeat;
  outline: none;
}
.login2 {
  margin-top: 28px;
  height: 46px;
  width: 80%;
  border: none;
  line-height: 44px;
  border-radius: 50px;
  background: #00b14b;
  color: #fff;
  font-size: 16px;
  letter-spacing: 2px;
  cursor: pointer;
  transition: 0.3s all linear;
}

.login2:hover {
  transition: 0.2s all linear;
  background: #00a32c;
}
.login-code {
  height: 38px;
  margin-top: 1px;
  cursor: pointer;
  bottom: 1px;
  right: 0px;
  position: absolute;
}
.code-container {
  position: relative;
  width: 80%;
}
.logo {
  position: absolute;
  top: 30px;
  left: 30px;
  background-image: url(../assets/logo/logo.png);
  background-repeat: no-repeat;
  background-size: contain;
  width: 150px;
  height: 117px;
}
</style>

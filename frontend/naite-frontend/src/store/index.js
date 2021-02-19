import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo:{},
    savedId:'',
    autoLogin:{},
    loginState:{},
    
  },
  mutations: {
    SAVE_USER_INFO:function(state,decoded){
      state.userInfo = decoded
    },
    SAVE_ID:function(state,userId){
      state.savedId = userId
    },
    AUTO_LOGIN:function(state,param){
      state.autoLogin = param
    },
    SAVE_LOGIN_STATE:function(state,params){
      state.loginState=params
    },
    LOGOUT:function(state){
      state.loginState.autologin=false
    }
    
  },
  actions: {
    saveuserinfo:function({commit},decoded){
      commit('SAVE_USER_INFO',decoded)
    },
    saveId:function({commit},userId){
      commit('SAVE_ID',userId)
    },
    autologin:function({commit},param){
      commit('AUTO_LOGIN',param)
    },
    saveloginstate:function({commit},params){
      commit('SAVE_LOGIN_STATE',params)
    },
    logout:function({commit}){
      commit('LOGOUT')
    }
    
  }, 
  getters: {

  },
  plugins: [
    createPersistedState()
  ]
})

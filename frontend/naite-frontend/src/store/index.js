import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo:{},
  },
  mutations: {
    SAVE_USER_INFO:function(state,decoded){
      state.userInfo = decoded
    }
  },
  actions: {
    saveuserinfo:function({commit},decoded){
      commit('SAVE_USER_INFO',decoded)
    }
  },  
  plugins: [
    createPersistedState()
  ]
})

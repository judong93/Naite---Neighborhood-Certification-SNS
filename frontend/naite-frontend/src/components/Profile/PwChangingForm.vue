<template>
  <form action="#" class='pw-changing-form'>
    <label for="now-pw">현재 비밀번호</label><br>
    <input type="password" id='now-pw' 
    v-model="nowPw" @keypress.space='checkSpace'><br>
    <label for="new-pw">새 비밀번호</label><br>
    <input type="password" id='new-pw' 
    v-model="newPw" @keypress.space='checkSpace'><br>
    <label for="new-pw-check">새 비밀번호 확인</label><br>
    <input type="password" id='new-pw-check' 
    v-model="newPwConfirm" @keypress.space='checkSpace'><br>
    <button class="setting-btn">확인</button>
  </form>
</template>

<script>
export default {
  name: 'PwChangeForm',
  data: function () {
    return {
      nowPw: '',
      newPw: '',
      newPwConfirm: '',
    }
  },
  methods: {
    checkSpace:function () {
      event.returnValue=false;
    },
  }
}
</script>

<style>

</style>
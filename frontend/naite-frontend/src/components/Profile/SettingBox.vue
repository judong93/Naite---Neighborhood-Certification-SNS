<template>
  <div id="settingform">
    <i class="fas fa-window-close fa-2x close-btn" @click="closeForm"></i>
    <div class="form-title">{{formTitle}}</div>
    <PwChangingForm v-if="formTitle==='비밀번호 재설정'" @pwChangingCompleted="changingCompleted" />
    <LocationChangingForm v-if="formTitle==='동네 재설정'" @changingAddressCompleted="changingCompleted" />
    <SelectUserForm v-if="formTitle==='거래에 참여한 유저를 선택해주세요'" :userJoinList='userJoinList' @selectJoinUser="selectJoinUser" :boardNo='boardNo' />
    <UserEvaluatingForm v-if="formTitle==='평가를 남겨주세요!'" :selectedUserNick='selectedUserNick' :MarketNo='MarketNo' :isSeller="isSeller" @evalCompleted="evalCompleted"/>
    <ProfileImgChangingForm v-if="formTitle==='프로필 이미지 변경'" @changingImgCompleted="changingCompleted" />
    <EvalShowingForm v-if="formTitle==='평가 내용'" :MarketNo='MarketNo' />
  </div>
</template>

<script>
import PwChangingForm from '@/components/Profile/PwChangingForm'
import LocationChangingForm from '@/components/Profile/LocationChangingForm'
import SelectUserForm from '@/components/Profile/SelectUserForm'
import UserEvaluatingForm from '@/components/Profile/UserEvaluatingForm'
import ProfileImgChangingForm from '@/components/Profile/ProfileImgChangingForm'
import EvalShowingForm from '@/components/Profile/EvalShowingForm'

export default {
  name: 'SettingBox',
  data: function () {
    return {
      selectedUserNick: '',
      MarketNo: 0,
    }
  },
  components: {
    PwChangingForm,
    LocationChangingForm,
    SelectUserForm,
    UserEvaluatingForm,
    ProfileImgChangingForm,
    EvalShowingForm,
  },
  props: {
    formIsOpen: Boolean,
    formTitle: String,
    userJoinList: Array,
    selectedMarketNo: Number,
    isSeller: Number,
    boardNo:[Number,String]
  },
  methods: {
    closeForm: function () {
      const settingForm = document.getElementById('settingform')
      settingForm.style.display = 'none'
    },
    changingCompleted: function (imgUrl) {
      const settingForm = document.getElementById('settingform')
      settingForm.style.display = 'none'
      this.$emit('changeImg',imgUrl)
    },
    selectJoinUser: function (userNick) {
      this.formTitle = '평가를 남겨주세요!'
      this.selectedUserNick = userNick
      console.log(userNick)
    },
    evalCompletd: function () {
      const settingForm = document.getElementById('settingform')
      settingForm.style.display = 'none'
      this.$emit('evalCompleted')
    }
  },
  watch: {
    formIsOpen: function () {
      const settingForm = document.getElementById('settingform')
      settingForm.style.display = 'block'
    },
    selectedMarketNo: function () {
      this.MarketNo = this.selectedMarketNo
    }
  },
}
</script>

<style>
#settingform {
  position: absolute;
  display: none;
  top: 23%;
  width: 20%;
  height: 64.2%;
  /* border: 3px solid red; */
  z-index: 2;
  background-color: #fae35f;
  border-radius: 10px;
  left: 40%;
  right: 40%;
}
#settingform input {
  border-style: dotted;
  border-top: none;
  border-left: none;
  border-right: none;
  margin-bottom: 20px;
}
.close-btn {
  transform: translateX(175px);
  cursor: pointer;
}
.form-title {
  font-size: 25px;
  margin: 10% 0;
}
.setting-btn {
    position: absolute;
    width: 50%;
    height: 8%;
    top: 70%;
    left: 50%;
    color:white;
    background-color: rgb(102, 102, 245);
    border-radius: 10px;
    border: none;
    transform:translateX(-50%)
}
.setting-btn:hover {
  width: 54%;
  height: 9%;
  transition-duration: 0.3s;
}
@media screen and (max-width: 501px) {
  #settingform {
    width: 60%;
    height: 67%;
    left: 20%;
    right: 20%;
    top: 14%;
  }
  .setting-btn {
    top: 77%;
  }
  .close-btn {
    transform: translate(133px);
  }
}
</style>
<template>
  <div id="settingform">
    <i class="fas fa-window-close fa-2x close-btn" @click="closeForm"></i>
    <div class="form-title">{{formTitle}}</div>
    <PwChangingForm v-if="formTitle==='비밀번호 재설정'" />
    <LocationChangingForm v-if="formTitle==='동네 재설정'" />
  </div>
</template>

<script>
import PwChangingForm from '@/components/Profile/PwChangingForm'
import LocationChangingForm from '@/components/Profile/LocationChangingForm'

export default {
  name: 'SettingForm',
  data: function () {
    return {
      
    }
  },
  components: {
    PwChangingForm,
    LocationChangingForm
  },
  props: {
    formIsOpen: Boolean,
    formTitle: String,
  },
  methods: {
    closeForm: function () {
      const settingForm = document.getElementById('settingform')
      settingForm.style.display = 'none'
    }
  },
  watch: {
    formIsOpen: function () {
      const settingForm = document.getElementById('settingform')
      settingForm.style.display = 'block'
    }
  }
}
</script>

<style>
#settingform {
  position: absolute;
  display: none;
  top: 18%;
  width: 20%;
  height: 70%;
  /* border: 3px solid red; */
  z-index: 1;
  background-color: white;
  border-radius: 10px;
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
  font-size: 30px;
  margin: 50px 0;
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
</style>
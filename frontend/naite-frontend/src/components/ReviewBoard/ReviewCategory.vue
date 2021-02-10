<template>
    <div id='reviewcategory'>
        <div class="reviewShowCategory" @click='toggleMenu(-1)'>카테고리 </div>
        <div class="reviewAll"  @click='toggleMenu(0)'>전체글 </div>
        <div class="reviewFood"  @click='toggleMenu(1)'>식당 <span>&#xf2e7;</span></div>
        <div class="reviewHospital" @click='toggleMenu(2)'>의료 <span>&#xf0f9;</span></div>
        <div class="reviewHealth" @click='toggleMenu(3)'>체육시설 <span>&#xf70c;</span></div>
        <div class="reviewBeauty" @click='toggleMenu(4)'>미용 <span>&#xf0c4;</span></div>
        <div class="reviewEtc" @click='toggleMenu(5)'>기타 <span>&#xf563;</span></div>
    </div>
</template>
<script>
import _ from 'lodash'


export default {
    name:'ReviewCategory',
    data:function(){
        return{
            toggled:false,  
            selectedIdx:-1,                     
        }
    },
    methods:{
        toggleMenu:function(num){
            let btnColor = ['#3569a5','#00c8fc','#fcba27','#dd4b39','#f7e600','#2db400','#A87A4F']
            if (this.toggled){
                this.selectedIdx = num
                const categorys = document.querySelectorAll('#reviewcategory > div')
                let i = 0
                categorys.forEach(category => {                                                            
                    category.style.visibility='hidden'
                    if (i===this.selectedIdx+1){
                        let selectedNum = _.random(0,btnColor.length-1)
                        category.style.backgroundColor = btnColor[selectedNum] 
                        category.style.visibility='visible'   
                    } else {
                        category.style.backgroundColor = 'transparent'
                    }
                    category.style.left = '0%'                     
                    i+=1
                });
                this.toggled=false
            } else {
                const categorys = document.querySelectorAll('#reviewcategory > div')
                let i = 0
                categorys.forEach(category => {                    
                    let selectedNum = _.random(0,btnColor.length-1)
                    const left = i*120                    
                    category.style.visibility='visible'
                    category.style.backgroundColor = btnColor[selectedNum]                    
                    category.style.left = `${left}px` 
                    btnColor.splice(selectedNum,1)                    
                    i+=1
                });
                this.toggled=true
            }
        },

    },
    watch:{
        selectedIdx:function(){
            this.$emit('changeSmallCategory',this.selectedIdx)
        }
    }
}
</script>

<style>
#reviewcategory {
    position: absolute;
    left:2%;    
    color:white;
    text-align: center;
    display: flex;
}

#reviewcategory > div {
    position:absolute;
    height:30px;
    border-radius: 10px;
    transition:0.3s;
    width: 100px;
    background-color: transparent;
    visibility: hidden;    
    left:0;
    cursor:pointer;
    opacity: 0.8;
}


#reviewcategory span {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;
}


#reviewcategory > div:nth-of-type(1){
    background-color: #66ec4b; 
    visibility: visible;
}

#reviewcategory > div:hover {
    opacity: 1;
    width: 120px ;    
}





</style>
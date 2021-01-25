<template>
    <div id="boardlist">
        <div class="listTitle" v-for='(list,idx) in test' :key='idx'>

            
            <div class="leftTitle" v-if='idx%2==0'>
                <div class='listimg'>
                    <img src="../assets/cha2.png" alt="">
                    
                </div>
                <div class='listtitle'>
                    {{list.title}}
                </div>
            </div>
            <div class="rightTitle" v-else>

            </div>

        </div>
    </div>
    
</template>
<script>



export default {
    name:'BoardList',
    components:{
                
    },
    data: function() {
        return {
            test:[
                {'title':'안녕하세요 이번에 여기로 이사온 이동희에요 호호호','user':'이동희'},{'title':'여기 맛집아시는분?','user':'김민경'},{'title':'동네 괜찮나요?','user':'박주동'},
                {'title':'안녕하세요 이번에 여기로 이사온 이동희에요 호호호','user':'이동희'},{'title':'여기 맛집아시는분?','user':'김민경'},{'title':'동네 괜찮나요?','user':'박주동'},
                {'title':'안녕하세요 이번에 여기로 이사온 이동희에요 호호호','user':'이동희'},{'title':'여기 맛집아시는분?','user':'김민경'},{'title':'동네 괜찮나요?','user':'박주동'},
                {'title':'안녕하세요 이번에 여기로 이사온 이동희에요 호호호','user':'이동희'},{'title':'여기 맛집아시는분?','user':'김민경'},{'title':'동네 괜찮나요?','user':'박주동'},
                {'title':'안녕하세요 이번에 여기로 이사온 이동희에요 호호호','user':'이동희'},{'title':'여기 맛집아시는분?','user':'김민경'},{'title':'동네 괜찮나요?','user':'박주동'}
            ]
        }
    },
    props:{
        onDetail:Boolean,
    },
    methods:{

        
    },
    computed: {

    },
    watch:{
        onDetail:function(){
            const listbox=document.getElementById('boardlist')
            if (this.onDetail){
                listbox.style.width ='20%'
                listbox.style.height ='75%'
                listbox.style.left = '1%'
                listbox.style.top = '23%'

            } else {
                listbox.style.width ='80%'
                listbox.style.left = '10%'
                listbox.style.top = '33%'
                listbox.style.height ='65%'
            }

        }

    },

    
}
</script>

<style>
#boardlist {
    position:absolute;
    width: 80%;
    height: 65%;
    background-color: transparent;
    left: 10%;
    top: 33%; 
    transition: 0.3s;
    overflow:auto;
}

.leftTitle {
    display:flex;
    background-color: wheat;
}

.listimg {
    width: 100px;
    height: 100px;
    overflow: hidden;
    border-radius: 100%;
    border: solid black;
    margin-top: 30px;
}

.listimg > img {
    width:100px
}

.listtitle {
    margin:60px 30px;
}

</style>
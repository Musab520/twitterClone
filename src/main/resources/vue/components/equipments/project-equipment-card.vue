<template id="project-equipment-card">
   <div>
    <div  disabled="none"   v-show="setDisable(availability)" class="grey darken-4 block-card"></div>
     <div   disabled="none"   v-show="setDisable(availability)" class=" btn-block-card">
       <v-row class="d-flex justify-space-between align-center my-6 ml-4 mr-3">

         <v-col  align="center"
                 justify="center">
           <p  class=" px-3 title white--text text--white-2">someone already took this equipment. but don't worry we got you!</p>
         </v-col>
       </v-row>
       <v-row class=" mt-6 px-2 d-flex justify-center align-center">
         <v-col  style="
    position: absolute;
    bottom: 0px;
" cols="12" md="12" align="center"
                justify="center">
           <v-btn
               large
               @click="removeEquipmentAndFindEquivalent(id)"
               color="success"
               style="
    width: 100%;
"
           >
             REMOVE & FIND EQUIVALENT
           </v-btn>
         </v-col>
       </v-row>
     </div>
     <v-card
        class="mx-auto"
        outlined>
      <v-img
          height="160"
          :src="image ?? '/equipment-placeholder.png'"
      >
      </v-img>
      <v-row class="d-flex justify-space-between align-center my-2 ml-4 mr-3">
        <h6 class="title">{{ name }}</h6>
        <v-icon :color="getdocumentsIconColor(documents_atteched)">mdi-file-document-outline</v-icon>
      </v-row>
      <v-row class="d-flex justify-start align-center my-2 ml-4 mr-3">
        <template>
          <v-chip  v-if="type"
              class="body-2  mr-2 mb-1"
              color="blue"
              label
              small
              outlined>
            {{ type }}
          </v-chip>
          <v-chip v-if="manufacturer"
              class="body-2 mr-2 mb-1"
              color="blue"
              label
              small
              outlined>
            {{ manufacturer }}
          </v-chip>
          <v-chip v-if="serialnumber"
              class="body-2 mr-2 mb-1"
              color="blue"
              label
              small
              outlined>
            {{ serialnumber }}
          </v-chip>
        </template>
      </v-row>
      <v-card-actions>

        <v-dialog v-model="removeEquipmentDialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-show="setDisable(availability)==false"
                outlined
                block
                large
                @click="removeEquipmentDialog = true"
                color="red">
              REMOVE
            </v-btn>
          </template>

          <v-card>
            <v-card-title class=""> Remove Equipment</v-card-title>
            <v-card-text> Are you sure you want to remove this equipment? </v-card-text>
            <v-card-actions >
              <v-spacer></v-spacer>
              <v-btn text @click="removeEquipmentDialog = false"> Cancel </v-btn>
              <v-btn color="red" text @click="removeEquipment(id)"> Remove </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-actions>
    </v-card>
  </div>

</template>
<script>
Vue.component("project-equipment-card", {
  template: "#project-equipment-card",
  props: {
    id: {
      type: String,
      required: true
    },
    entity_name: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    manufacturer: {
      type: String,
      required: true
    },
    serialnumber: {
      type: String,
      required: true
    },
    type: {
      type: String,
      required: true
    },
    availability: {
      type: String,
      required: true
    },
    image: {
      type: String,
      required: true
    } ,
    documents_atteched:{
      type:Boolean ,
      required: true
    }
  },
  data(){
    return{
      entity_name:"equipment",
      removeEquipmentDialog:false
    }
  }
  , created() {
        this.entity_name="equipment"
      }
  ,methods:
      {

        getdocumentsIconColor(documents_atteched)
        {
          if(documents_atteched == true) {return 'accent'}
          else {return 'orange lighten-4'}
        },
         setDisable(availability)
         {
          return  availability != 'Available'
         },

        removeEquipment(id)
        {
          this.$emit('remove-equipment', { equipmentid: id })
        },
        removeEquipmentAndFindEquivalent(id)
        {
          this.$emit('remove-equipment-and-find-equivalent', { equipmentid: id })
        }


      }
});
</script>
<style>

.block-card{
  z-index: 1;
  position: absolute;
  opacity: 0.5;
  width: 371.328px;
  height: 306px;
}
.btn-block-card{
  z-index: 3;
  position: absolute;
  width: 371.328px;
  height: 306px;
}
</style>
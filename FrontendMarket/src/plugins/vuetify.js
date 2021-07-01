import Vue from "vue";
import Vuetify from "vuetify/lib/framework";
import { ToastPlugin } from "bootstrap-vue";
import VueBootstrapToasts from "vue-bootstrap-toasts";
import Toasted from "vue-toasted";


Vue.use(Toasted)
Vue.use(VueBootstrapToasts);
Vue.use(ToastPlugin)

Vue.use(Vuetify);

export default new Vuetify({});

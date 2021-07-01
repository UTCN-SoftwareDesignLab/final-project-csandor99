import authHeader, { BASE_URL, HTTP } from "../http";

export default {

    countEmployeeActivity(name) {
        return HTTP.get(BASE_URL + "/activity/promo/" + name,{ headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    generateActivityReport(type) {
        return HTTP.get(BASE_URL + "/activity/export/" + type, {
            responseType: "arraybuffer",
            headers: authHeader(),
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            document.body.appendChild(link);
            link.href = url;
            link.setAttribute("download", "ActivityReport.pdf");
            link.click();
        });
    },
};

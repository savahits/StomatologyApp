// Initialize TomSelect for specialization dropdown
document.addEventListener("DOMContentLoaded", function () {
    const settings = {
        create: false,
        sortField: { field: "text", direction: "asc" },
        placeholder: "Введите специализацию",
        allowEmptyOption: true,
        maxOptions: 100,
    };
    const ts = new TomSelect("#specializationSelect", settings);
    if (!document.querySelector("#specializationSelect").value) {
        ts.clear();
    }
});


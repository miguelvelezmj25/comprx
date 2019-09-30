x = [5, 16, 24, 4];
y = [0.8, 1.94, 1.33, 1.1];
n = ["FW", "PW"', "SAD", "CC"];

scatter(x, y, 1500, '.');
text(x, y, n, 'horizontal','left', 'vertical','bottom')
xlabel('Cost [Configurations]');
ylabel('Prediction Error [MAPE]');
title('Pngtastic Counter');
ylim([0.7 2])
fontset
 
fig = gcf;
fig.PaperPositionMode = 'auto';
fig_pos = fig.PaperPosition;
fig.PaperSize = [fig_pos(3) fig_pos(4)];

mkdir('../../../../../../../../resources/evaluation/programs/java/pngtasticColorCounter/plots/');
fileID = '../../../../../../../../resources/evaluation/programs/java/pngtasticColorCounter/plots/prediction_error.pdf';
print(fig, fileID,'-dpdf');
fileID = '../../../../../../../../resources/evaluation/programs/java/pngtasticColorCounter/plots/prediction_error.png';
print(fig, fileID,'-dpng');